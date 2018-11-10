## LET

push 6
push 5
push 4
push 3
push B_class
new

## IN

lfp
push -1
lfp
add
lw
cts
lw
push 5
add
lm
js
lfp
push -1
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
push -1
lfp
add
lw
cts
lw
push 6
add
lm
js
halt

## Functions code and Dispatch Table

function0:
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

function1:
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

function2:
cfp
lra
lfp
push 0
lfp
add
lw
cts
lw
push 4
add
lm
js
lfp
push 0
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
push 65
srv
sra
pop
sfp
lrv
lra
js

function4:
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

function5:
cfp
lra
push 3
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

A_class:
function0
function1
B_class:
function0
function1
function2
function3
function4
function5
