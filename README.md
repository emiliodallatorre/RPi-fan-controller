# rpi-fan-control [![Build Status](https://travis-ci.org/emiliodallatorre/rpi-fan-controller.svg?branch=kotlin)](https://travis-ci.org/emiliodallatorre/rpi-fan-controller)
Temperature-based control of Raspberry Pi-3 fan through GPIO port.

This package is the sources of the **rpi-fan-control** app that checks temperature of the Raspberry Pi CPU and controls fan through one of the GPIO pins.

## Installation (.apk package)

Dependencies:
* fakeroot

You can build .deb package using Makefile:
```
make deb
```
And then install it with ```dpkg```:
```
sudo dpkg -i rpi-fan-control-*.deb
```

## Configuration

There is config file /etc/rpi-fan-control/rpi-fan-control.conf in which you can change
* GPIO pin which will contol the fan
* Upper and lower temperature triggers
* Temperature refresh delay

On this picture you can see available GPIO pin numbers:

<img src="https://docs.oracle.com/javame/config/cldc/rel/3.3/rasp/gs/html/getstart_raspi/img/pinout.jpg" />
