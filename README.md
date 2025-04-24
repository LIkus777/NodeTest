# NodeTest

NodeTest is a application for working with tree-like structures of `Node` objects, providing utilities for:

- **Generating stable display names** (hash-based) via `DisplayNodeName`.
- **Recursively adding new child nodes** to an existing tree by matching a specified `parentId`.
- **Immutable transformations** of `Node` trees.

## Features

### **DisplayNodeName:**
- **Generates a 40-character hexadecimal identifier** for any `Node`, based on the last 20 bytes of its SHA-256 hash.

### **Immutable Tree Operations:**
- **Recursively traverse and transform `Node` trees** without mutating original data.
