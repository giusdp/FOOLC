## LET


## IN

push 3
push 1
push A_class
new
lfp
push -1
add
sw
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
push 1
lfp
lw
add
lw
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

A_class:
function0
function1
