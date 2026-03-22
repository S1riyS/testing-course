from math import sin, pi

step = pi / 2
x = 0.0 - 2*pi
while x <= 0:
    try:
        print(f"{round(x, 9)};{round(1/sin(x), 9)}")
    except Exception as e:
        print(f"{round(x, 9)};ArithmeticException")
    
    x += step

