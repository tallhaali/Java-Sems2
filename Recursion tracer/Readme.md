# 🧠 Recursion Tracer Projects

This collection includes tools written in **Python** and **Java** that help you understand how recursion works through **step-by-step visual tracing**.

---

## 📂 Contents

### 1. `tracer.py` — *(Python)*

A powerful visual learning tool that traces recursive functions in the terminal with explanations and color-coded output.

#### ✅ Traced Functions:
- `factorial(n)`
- `fibonacci(n)`
- `sum_to_n(n)`
- `reverse_string(s)`
- `is_palindrome(s)`

#### 💡 Features:
- Highlights **base case** and **recursive step**
- Uses **indentation** to reflect recursion depth
- Explains what the function is doing at each step
- Uses `colorama` (optional) to color the output for better clarity

#### ▶️ How to Run:
```bash
python tracer.py
```

#### Optional: Install `colorama` for colors
```bash
pip install colorama
```

---

### 2. `Tracer.java` — *(Java Maze Solver with Recursion)*

A fun recursion tracer that generates a **random 4x4 maze** and tries to solve it using recursion and backtracking.

#### 🧩 Features:
- Random walls each time you run
- Clearly logs each move
- Shows recursive decisions and backtracks
- Teaches recursion in a real-world scenario

#### ▶️ How to Compile & Run:
```bash
javac Tracer.java
java Tracer
```

#### 🔍 Sample Output:
```
At position (0, 0)
Exploring neighbors...
Trying to move right...
Hit a wall at (0, 1). Backtracking.
Trying to move down...
...
Reached the goal at (3, 3)!
```

---

## 🎓 Learning Goals

These projects are designed to:
- Help you visualize the **call stack**
- Understand **base vs recursive cases**
- Learn by **seeing how functions work step-by-step**
- Build deep understanding of recursion with **live tracing**

---

## 📦 Ideas for Further Improvement

- Add `input()` pauses in Python version to go step-by-step slowly
- Track number of calls made
- Add visual GUI in future (e.g., using Tkinter or Java Swing)
- Add challenges like Tower of Hanoi or Merge Sort for more practice

---

## ❤️ Credits & Notes

These were built to make recursion **less scary** and more fun to learn. Feel free to:
- Modify them for your own learning style
- Use them in class
- Share with friends
