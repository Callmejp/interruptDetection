## Interrupt Conflict Detection

Mainly based this [idea](http://www.jos.org.cn/ch/reader/create_pdf.aspx?file_no=4980&journal_id=jos)
and [Antlr](https://github.com/antlr)(lexer&parser analysis support).

### Restriction

1. We assume the interrupt program happens once at most.
2. Interrupt_1 can grab the MAIN; Interrupt_2 can grab the MAIN & Interrupt_1.

### Our Measure

Brutal Force. We assume the exact place where INTER_1 & INTER_2
will happen, then we mock program runs and record
the Read/Write Operator to the global variables.

We use multithreading to simulate the implementation of each subroutine running.

### TODO
- [x] Support to Array. (For now, we just treat Array as a single Variable.)
- [] Possible Read/Write happens in the IF CONDITION / FOR CONDITION.
   ```c
   if (here) {
       x = 1;   
   }
   
   for (here) {
       x = 1;
   }
   ```
- [x] More Clear Output Format.