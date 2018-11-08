## LET

push 0
push B_class
new

## IN

lfp
push -1
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
push 119
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
push 118
srv
sra
pop
sfp
lrv
lra
js

A_class:
function0
B_class:
function1
