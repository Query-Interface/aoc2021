package com.queryinterface.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

public class Packet
{
    final static int LITERAL = 4;
    private static Map<Character, String> hexMap;
    
    private int version;
    private int typeID;
    private long value;
    private List<Packet> packets = new ArrayList<>();

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

    public Packet() {

    }

    public Packet(int version, int type, long value) {
        this.version = version;
        this.typeID = type;
        this.value = value;
    }

    public void addPacket(final Packet p) {
        this.packets.add(p);
    }

    public void addPackets(final List<Packet> pp) {
        this.packets.addAll(pp);
    }

    public List<Packet> getPackets() {
        return this.packets;
    }
    
    public int getVersion() {
        return this.version;
    }

    public PacketType getType() {
        return PacketType.valueOf(this.typeID).get();
    }

    public long getValue() {
        switch (getType()) {
            case SUM:
                return sumOfPacketValues();
            case PRODUCT:
                return muliplyOfPacketValues();
            case MINIMUM:
                return minValue();
            case MAXIMUM:
                return maxValue();
            case GREATER_THAN:
                return packets.get(0).getValue() > packets.get(1).getValue() ? 1: 0;
            case LESS_THAN:
                return packets.get(0).getValue() < packets.get(1).getValue() ? 1: 0;
            case EQUAL_TO:
                return packets.get(0).getValue() == packets.get(1).getValue() ? 1: 0;
            case LITERAL:
            default:
                return this.value;
        }
    }

    private long maxValue() {
        long max = packets.get(0).getValue();
        for (int i=1; i<packets.size(); i++) {
            if (max < packets.get(i).getValue()) {
                max = packets.get(i).getValue();
            }
        }
        return max;
    }

    private long minValue() {
        long min = packets.get(0).getValue();
        for (int i=1; i<packets.size(); i++) {
            if (min > packets.get(i).getValue()) {
                min = packets.get(i).getValue();
            }
        }
        return min;
    }

    private long sumOfPacketValues() {
        long count = 0;
        for (var p: packets) {
            count += p.getValue();
        }
        return count;
    }

    private long muliplyOfPacketValues() {
        long count = 1;
        for (var p: packets) {
            count *= p.getValue();
        }
        return count;
    }

    public boolean isLiteral() {
        return typeID == LITERAL;
    }
}