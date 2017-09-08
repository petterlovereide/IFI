	.extern	fread, fwrite

	.text
	.globl	_writebyte

_writebyte:
	pushl	%ebp
	movl	%esp,%ebp

	pushl	8(%ebp)
	pushl	$1
	pushl	$1
	leal	12(%ebp), %eax
	pushl	%eax

	call	fwrite

	addl	$16, %esp

	popl	%ebp
	ret

	.globl _readbyte

_readbyte:
	pushl	%ebp
	movl	%esp,%ebp

	leal	8(%ebp), %eax
	pushl	%eax
	pushl	$1
	pushl	$1
	pushl

	call	fread

	addl	$16, %esp

rb_x:	popl	%ebp
	ret




	.globl _writeutf8char

_writeutf8char:
	pushl	%ebp
	movl	%esp,%ebp

wu8_x:	popl	%ebp
	ret



	.globl	_readutf8char

_readutf8char:
	pushl %ebp
	movl	%esp,%ebp

	popl	%ebp
	ret
