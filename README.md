# Integrative Project

## Part 1
**A Java program that reads from a file the elements that define an DFA and builds the equivalent minimized DFA. Also, the program say if a string is accepted or not by any DFA.**

The transition table needs to be defined in a *txt file*. The file shall be defined as follows:
- The first line indicates the set of states of the automata separated by commas.
- The second line indicates the alphabet symbols separated by commas.
- The third line indicates the initial state.
- The fourth line indicates the set of final states separated by commas.
- The following lines indicate the evaluation of of the extended transition function with the elements of the alphabet in the following format:
  - state, symbol = > state
    - Example, the following line `q0, a = > q1` indicates that the DFA processes the following: δ(q0,a) = q1

It is not necessary that all transitions are specified in this file. An evaluation may not appear if a state indicating that the result of that evaluation is the empty set.

The program display the states of the minimized automaton or ask for a string and show the result of the evaluation (step by step) of the string from the initial state in order to know if the string is accepted by this automaton.


## Part 2
**A Java program that reads from a file the elements that define a context-free grammar and apply the top-down parsing process for strings given by the user.**

The grammar needs to be defined in a *txt file*. The file shall be defined as follows:
- The first line indicates the set of non-terminal symbols separated by commas, only one uppercase character.
- The second line indicates the set of terminal symbols separated by commas, only one lowercase character.
- The third line indicates the start symbol.
- The following lines indicate the productions of the grammar in the following format:
  - non-terminalSymbol->chain terminals or non-terminal symbol

Lambda cannot appear as body of any production. The top-down parsing process will receive a string and an integer. The integer indicates the maximum depth of the parsing tree. If this depth is exceeded, the program stop the process indicating that no solution was found for the string.

The outcome of the process must be the parsing tree.


###### Important
> Both programs don't validate the values in the input file and suppose that were built correctly.