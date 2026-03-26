
| Data Structure | Operation | Big-O | Why |
|---|---|---|---|
| ArrayList | add at end | O(1) amortized | Appends to end of array |
| ArrayList | add at beginning | O(n) | Shifts all elements right |
| ArrayList | get by index | O(1) | Direct index access |
| ArrayList | search by name | O(n) | Must scan each element |
| ArrayList | remove middle | O(n) | Shifts elements after removal |
| LinkedList | add first | O(1) | Just updates head pointer |
| LinkedList | add last | O(1) | Just updates tail pointer |
| LinkedList | search | O(n) | Must traverse node by node |
| LinkedList | remove | O(n) | Must find the node first |
| HashMap | put | O(1) avg | Hashes key directly to bucket |
| HashMap | get | O(1) avg | Hashes key to find value |
| HashMap | containsKey | O(1) avg | Same as get |
| HashSet | add | O(1) avg | Hashes value to check/insert |
| HashSet | contains | O(1) avg | Hashes value to find bucket |
| TreeMap | put | O(log n) | Balanced tree insertion |
| TreeMap | get | O(log n) | Balanced tree traversal |
| TreeMap | iteration | O(n) | Visits every node in order |
| Stack | push | O(1) | Adds to top |
| Stack | pop | O(1) | Removes from top |
| Stack | peek | O(1) | Reads top without removing |
| Queue | enqueue | O(1) | Adds to tail |
| Queue | dequeue | O(1) | Removes from head |
| Queue | peek | O(1) | Reads head without removing |


## Performance Results

### ArrayList vs LinkedList Comparison
```
********* Comparison Table ************
Add at beginning -> ArrayList: 0.0095 ms,  LinkedList: 0.007542 ms
Add at end       -> ArrayList: 0.002416 ms, LinkedList: 0.001125 ms
Search by name   -> ArrayList: 0.141542 ms, LinkedList: 0.25225 ms
Remove from middle -> ArrayList: 0.005292 ms, LinkedList: 0.006458 ms
```

### HashMap vs ArrayList Lookup Comparison
```
ArrayList total search time: 0.023708 ms
HashMap total search time:   0.005958 ms
```
