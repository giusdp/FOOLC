## LET

push 55
push 0
push A_class
new

## IN

lfp
push -2
lfp
add
lw
cts
lw
push 1
add
lm
js
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 42
srv
sra
pop
sfp
lrv
lra
js

A_class:
function0
