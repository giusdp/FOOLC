## LET

push 41
push 1
push B_class
new

## IN

lfp
push 420
push -1
lfp
add
lw
cts
lw
push 3
add
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
sfp
lrv
lra
js

function1:
cfp
lra
push 1
lfp
add
lw
lfp
lw
push 1
add
sw
sra
pop
pop
sfp
lra
js

function2:
cfp
lra
lfp
push 1
lfp
add
lw
push 0
lfp
add
lw
cts
lw
push 2
add
lm
js
sra
pop
pop
sfp
lra
js

A_class:
function0
B_class:
function0
function1
function2
