## LET

push function1

## IN

lfp
lfp
push -1
lfp
add
lw
js
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 1
lfp
lw
add
lw
push 55
lfp
lw
push 1
add
sw
push 1
lfp
lw
add
lw
srv
pop
sra
pop
sfp
lrv
lra
js

function1:
cfp
lra
push 4
push 1
push A_class
new
lfp
push -2
lfp
lw
add
lw
cts
lw
push 1
add
lm
js
srv
pop
sra
pop
sfp
lrv
lra
js

A_class:
function0
