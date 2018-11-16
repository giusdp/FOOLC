## LET

push function2
push 0
push A_class
new

## IN

push 3
push 1
push A_class
new
lfp
push -2
add
sw
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
push 4
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
push 5
lfp
lw
push 1
add
sw
lfp
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
push 118
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

function2:
cfp
lra
push 5
pop
sra
pop
sfp
lra
js

A_class:
function0
function1
