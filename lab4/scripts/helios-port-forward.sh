#!/bin/sh

exec ssh -f -N -L 8079:stload.se.ifmo.ru:8080 s413732@se.ifmo.ru -p 2222
