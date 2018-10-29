## LET

push function2

## IN

lfp
push 7
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

function2:
cfp
lra
push 1
lfp
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

A_class:
function0
function1
