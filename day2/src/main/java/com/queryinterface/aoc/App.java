package com.queryinterface.aoc;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App
{
    public static void main( String[] args ) throws IOException
    {
		int forward = 0;
		int depth = 0;
		int aim = 0;
		try (InputStream stream = App.class.getClassLoader().getResourceAsStream("input.dat")) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] tuple = line.split(" ");
					int value = Integer.parseInt(tuple[1]);
					switch(tuple[0]) {
						case "forward":
							forward += value;
							depth += value * aim;
							break;
						case "up":
							aim -= value;
							break;
						case "down":
							aim += value;
							break;
					}
				}
			}
		}
        System.out.println(forward * depth);
    }
}