## Interrupt Conflict Detection

Mainly based this [idea](http://www.jos.org.cn/ch/reader/create_pdf.aspx?file_no=4980&journal_id=jos).

### Restriction

1. We assume the interrupt program happens once at most.
2. Interrupt_1 can grab the MAIN; Interrupt_2 can grab the MAIN & Interrupt_1.

### Our Measure

Brutal Force. We assume the exact place where INTER_1 & INTER_2
will happen(or not happen), then we mock program runs and record
the Read/Write Operator to the global variables.

**But if interrupt happens in the Loop Segment, which time
of the loop does it correspond to?**

So we break the Loop Segment into Loop Count * (Sequential Segment).

### TODO

1. Support to Array. (For now, we just treat Array as a single Variable.)
2. Possible Read/Write happens in the IF CONDITION / FOR CONDITION.
   ```c
   if (here) {
       x = 1;   
   }
   
   for (here) {
       x = 1;
   }
   ```
3. More Clear Output Format.