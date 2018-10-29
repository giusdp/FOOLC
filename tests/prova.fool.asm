## LET

push 811
push 118
push 2
push A_class
new

## IN

lfp
lfp
push -1
lfp
add
lw
ctop
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
lw
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

function1:
cfp
lra
push 2
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
