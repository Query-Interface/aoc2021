package com.queryinterface.aoc;

public class Display {
    private boolean segments[] = new boolean[7];

    public static Display of(String data) {
        var display = new Display();
        for (char c : data.toCharArray()) {
            switch (c) {
                case 'a':
                  display.a(true);
                  break;
                case 'b':
                  display.b(true);
                  break;
                case 'c':
                  display.c(true);
                  break;
                case 'd':
                  display.d(true);
                  break;
                case 'e':
                  display.e(true);
                  break;
                case 'f':
                  display.f(true);
                  break;
                case 'g':
                  display.g(true);
                  break;
            }
        }
        return display;
    }

    public int getValue() {
        if (a() && b() && c() && !d() && e() && f() && g()) {
            return 0;
        }
        if (c() && f() && !a() &&!b() &&!d() && !e() && !g()) {
            return 1;
        }
        if (a() &&!b() && c() && d() && e() && !f() && g()) {
            return 2;
        }
        if (a() &&!b() && c() && d() && !e() && f() && g()) {
            return 3;
        }
        if (!a() && b() && c() && d() && !e() && f()) {
            return 4;
        }
        if (a() && b() && !c() && d() && !e() && f() && g()) {
            return 5;
        }
        if (a() && b() && !c() && d() && e() && f() && g()) {
            return 6;
        }
        if (a() && !b() && c() && !d() && !e() && f()) {
            return 7;
        }
        if (a() && b() && c() && d() && e() && f() && g()) {
            return 8;
        }
        if (a() && b() && c() && d() && !e() && f() && g()) {
            return 9;
        }
        return 0;
    }

    public boolean a() {
        return segments[0];
    }
    public boolean b() {
        return segments[1];
    }
    public boolean c() {
        return segments[2];
    }
    public boolean d() {
        return segments[3];
    }
    public boolean e() {
        return segments[4];
    }
    public boolean f() {
        return segments[5];
    }
    public boolean g() {
        return segments[6];
    }

    public void a(boolean value) {
        segments[0] = value;
    }
    public void b(boolean value) {
        segments[1] = value;
    }
    public void c(boolean value) {
        segments[2] = value;
    }
    public void d(boolean value) {
        segments[3] = value;
    }
    public void e(boolean value) {
        segments[4] = value;
    }
    public void f(boolean value) {
        segments[5] = value;
    }
    public void g(boolean value) {
        segments[6] = value;
    }
}
