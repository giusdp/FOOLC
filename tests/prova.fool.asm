## LET

push 12
push 3
push 1
push B_class
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

A_class:
function0
B_class:
function0
