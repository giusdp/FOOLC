push 3
push 1
push A_class
new
push 44
push 22
push 2
push B_class
new
push 0
push C_class
new
halt

function0:
cfp
lra
push 3
srv
sra
pop
sfp
lrv
lra
js

P_class:
A_class:
B_class:
C_class:
function0
