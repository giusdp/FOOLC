## LET

push 0
push A_class
new
push function2
push function3

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
lfp
push -1
lfp
lw
lw
add
lw
cts
lw
push 2
add
lm
js
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
push 119
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
lfp
lfp
lw
push -2
lfp
lw
add
lw
js
srv
sra
pop
sfp
lrv
lra
js

function3:
cfp
lra
push 115
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
