## LET

push function1
push 0
push B_class
new
push 55
push 0
push A_class
new
push 4
push 0
push A_class
new

## IN

lfp
push -6
lfp
add
lw
cts
lw
push 1
add
lm
js
push -3
lfp
add
lw
push -6
lfp
add
lw
push -2
lfp
add
lw
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
push 44
lfp
push -3
add
sw
lfp
push -4
lfp
add
lw
cts
lw
push 1
add
lm
js
push -3
lfp
add
lw
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

function1:
cfp
lra
push 7
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
