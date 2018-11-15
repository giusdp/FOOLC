## LET

push function3
push 119
push 1
push B_class
new

## IN

lfp
lfp
push -1
lfp
add
lw
js
lfp
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
push 301
push -2
lfp
add
lw
cts
lw
push 3
add
lm
js
lfp
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
push 55
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
push 1
lfp
add
lw
push 1
lfp
lw
add
lw
add
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

function3:
cfp
lra
push 4
push 1
push A_class
new
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
B_class:
function0
function1
function2
