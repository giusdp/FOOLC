## LET

push 53
push 1
push A_class
new

## IN

lfp
push -1
lfp
add
lw
cts
lw
push 2
add
lm
js
print
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
srv
sra
pop
sfp
lrv
lra
js

function1:
cfp
lra
push 1
lfp
lw
add
lw
srv
sra
pop
sfp
lrv
lra
js

A_class:
function0
function1
