## LET

push 118
push 1
push A_class
new

## IN

lfp
push 19
lfp
push -1
lfp
add
lw
cts
lw
lm
js
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 1
lfp
add
lw
srv
sra
pop
pop
sfp
lrv
lra
js

A_class:
function0
