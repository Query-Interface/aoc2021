package com.queryinterface.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    private int cursor = 0;
    final static int LITERAL = 4;
    private static Map<Character, String> hexMap;
    private char data[];
    private List<Packet> packets;

    static {
        hexMap = new HashMap<Character, String>();
        hexMap.put('0', "0000");
        hexMap.put('1', "0001");
        hexMap.put('2', "0010");
        hexMap.put('3', "0011");
        hexMap.put('4', "0100");
        hexMap.put('5', "0101");
        hexMap.put('6', "0110");
        hexMap.put('7', "0111");
        hexMap.put('8', "1000");
        hexMap.put('9', "1001");
        hexMap.put('A', "1010");
        hexMap.put('B', "1011");
        hexMap.put('C', "1100");
        hexMap.put('D', "1101");
        hexMap.put('E', "1110");
        hexMap.put('F', "1111");
    }

    public List<Packet> parse(final String sData) {
        cursor = 0;
        packets = new ArrayList<>();
        data = getBytes(sData);

        Packet current;

        while (cursor < data.length) {
            int version = getVersion(Arrays.copyOfRange(data, cursor, cursor + 3));
            cursor += 3;
            int type = getType(Arrays.copyOfRange(data, cursor, cursor + 3));
            cursor += 3;
            current = parsePacket(version, type);
            packets.add(current);

            break;
        }
        return packets;
    }

    private Packet parsePacket(int version, int type) {
        Packet current;
        if (type == Packet.LITERAL) {
            long value = decodeLiteral();
            current = new Packet(version, type, value);
        } else {
            current = parseOperator(version, type);
        }
        return current;
    }

    private Packet parseOperator(int version, int type) {
        boolean isBitsLength = data[cursor++] == '0' ? true : false;
        var packet = new Packet(version, type, 0);
        if (isBitsLength) {
            int length = getLength();
            parseSubPacketsByLength(packet, length);
            
        } else {
            int nbPackets = getNumberOfPackets();
            parseSubPacketsByCount(packet, nbPackets);
        }
        return packet;
    }

    private void parseSubPacketsByLength(Packet packet, int length) {
        final int end = cursor + length;
        while (cursor < end) {
            if (cursor + 6 < end) {
                int version = getVersion(Arrays.copyOfRange(data, cursor, cursor + 3));
                cursor += 3;
                int type = getType(Arrays.copyOfRange(data, cursor, cursor + 3));
                cursor += 3;
                packet.addPacket(parsePacket(version, type));
            } else {
                readToCompletionOfPacket();
            }
        }
    }

    private void parseSubPacketsByCount(Packet packet, int nbPackets) {
        List<Packet> packets = new ArrayList<>();
        while (packets.size() < nbPackets) {
            int version = getVersion(Arrays.copyOfRange(data, cursor, cursor + 3));
            cursor += 3;
            int type = getType(Arrays.copyOfRange(data, cursor, cursor + 3));
            cursor += 3;
            packets.add(parsePacket(version, type));
        }
        packet.addPackets(packets);
    }

    private int getLength() {
        int value = (int) convertToDecimal(Arrays.copyOfRange(data, cursor, cursor+15));
        cursor += 15;
        return value;
    }

    private int getNumberOfPackets() {
        int value = (int) convertToDecimal(Arrays.copyOfRange(data, cursor, cursor+11));
        cursor += 11;
        return value;
    }

    private char[] getBytes(String data) {
        var bytes = java.util.HexFormat.of().parseHex(data);
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for( int i = 0; i < Byte.SIZE * bytes.length; i++ ) {
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        }
        return sb.toString().toCharArray();
    }


    private int getVersion(final char data[]) {
        return (int) convertToDecimal(data);
    }

    private int getType(final char data[]) {
        return (int) convertToDecimal(data);
    }

    private long decodeLiteral() {
        StringBuilder builder = new StringBuilder();
        boolean isLastDigit = false;
        while (!isLastDigit) {
            if (data[cursor] == '0') {
                isLastDigit = true;
            }
            for (int i=cursor+1; i< cursor+5; i++) {
                builder.append(data[i]);
            }
            cursor += 5;
        }
        return convertToDecimal(builder.toString().toCharArray());
    }

    private void readToCompletionOfPacket() {
        while ((cursor)%4 != 0) {
            cursor++;
        }
    }

    private long convertToDecimal(char[] digits) {
        long result=0;
        int length = digits.length - 1;
        for (int i=length; i >= 0; i--) {
          result += Character.getNumericValue(digits[i]) * (long) Math.pow(2, (length-i));
        }
        return result;
    }

    @Override
    public String toString() {
        return new String(this.data);
    }
}
