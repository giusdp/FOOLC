push 0
halt

function0:
cfp
lra
push 4
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
srv
sra
pop
sfp
lrv
lra
js

E_class:
function0
function1
