## LET

push function3
push 34
push 1
push A_class
new
push -4
lfp
add
lw
push 1
push Z_class
new

## IN

lfp
push -5
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
push 0
push A_class
new
lfp
push -2
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
lfp
push 1
lfp
add
lw
cts
lw
push 1
add
lm
js
srv
sra
pop
pop
sfp
lrv
lra
js

function3:
cfp
lra
lfp
push 1
lfp
add
lw
cts
lw
push 1
add
lm
js
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
Z_class:
function1
function2
