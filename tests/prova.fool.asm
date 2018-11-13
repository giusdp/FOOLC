## LET

push function1
push 0
push A_class
new
push 3

## IN

push -2
lfp
add
lw
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 4
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
push 4
srv
sra
pop
sfp
lrv
lra
js

A_class:
function0
