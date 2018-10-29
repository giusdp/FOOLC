## LET

push 2
push 118
push 2
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

function2:
cfp
lra
push 1
lfp
lw
add
lw
push 2
lfp
lw
add
lw
add
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
function2
