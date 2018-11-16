## LET

push function3
push function4
push 119
push 1
push B_class
new
push 0
push A_class
new

## IN

push 3
push 1
push A_class
new
lfp
push -4
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
push 4
lfp
lfp
push -2
lfp
add
lw
js
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
push 5
pop
sra
pop
sfp
lra
js

function4:
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
