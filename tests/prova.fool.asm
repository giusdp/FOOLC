## LET

push 0
push A_class
new
push 0
push B_class
new
push 0
push W_class
new
lfp
push -3
lfp
add
lw
push -2
lfp
add
lw
cts
lw
push 2
add
lm
js
lfp
push -4
lfp
add
lw
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

## IN

push 1
halt

## Functions code and Dispatch Table

function0:
cfp
lra
push 0
push W_class
new
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
push 0
push Z_class
new
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
B_class:
function0
function1
W_class:
Z_class:
