University Management System

This repository contains two versions of the **University Management System** project:

---

## 📌 Branches

### 1. `main` branch
- Contains the **original project** without security updates.
- Use this branch if you want the base version of the system.

### 2. `security-patches` branch
- Contains the **updated project with security improvements**.
- Use this branch if you want the enhanced and patched version.

---

## 🔒 Security Updates in `security-patches`

The following changes have been implemented to strengthen the project against common vulnerabilities:

1. **Password Security**
   - Plain-text passwords replaced with **hashed passwords** using a secure hashing algorithm.
   - Added password salting to improve resistance against brute-force and rainbow table attacks.

2. **SQL Injection Prevention**
   - All database queries now use **prepared statements** instead of string concatenation.
   - User inputs are properly parameterized to block SQL injection attempts.

3. **Input Validation**
   - Added strict validation and sanitization of user inputs.
   - Prevents malicious inputs such as script injection and invalid data.

4. **Improved Error Handling**
   - Removed exposure of sensitive database/system errors to the end user.
   - Added user-friendly error messages while logging technical details securely.

5. **Secure Session Management**
   - Implemented proper session handling.
   - Reduced risks of **session fixation** and **unauthorized access**.

6. **Project Structure Updates**
   - Updated file organization for better maintainability.
   - Added comments to highlight secure coding practices.

---

## 📊 Before vs After (Old Project vs Security-Patched Project)

| Feature / Concern         | `main` (Old Project)                            | `security-patches` (Updated Project)                  |
|----------------------------|------------------------------------------------|------------------------------------------------------|
| **Password Storage**       | Plain text in database                         | Hashed + Salted using secure algorithms              |
| **Database Queries**       | String concatenation (vulnerable to SQLi)      | Prepared statements with parameterized queries       |
| **Input Validation**       | Minimal / none                                 | Strict validation & sanitization applied             |
| **Error Handling**         | Exposes raw database/system errors             | User-friendly messages, secure error logging         |
| **Session Management**     | Basic / default                                | Hardened session handling, prevents fixation attacks |
| **Security Awareness**     | No security best practices                     | Secure coding guidelines & comments included         |

---

## 🔽 How to Download the Updated Project

### Option 1: Download as ZIP
1. Click on the branch dropdown at the top-left (it shows `main` by default).
2. Select **`security-patches`**.
3. Click the green **Code** button.
4. Select **Download ZIP** and extract it on your system.

### Option 2: Clone via Git
If you have Git installed, run:

```bash
# Clone the repo
git clone https://github.com/<your-username>/University-Management-System.git

# Navigate into the repo
cd University-Management-System

# Switch to the security-patches branch
git checkout security-patches
```
UMS before (main branch):
https://github.com/user-attachments/assets/cac0f6d6-8f28-4a4b-8355-8671d95424df

UMS after (security-patches branch):
https://github.com/user-attachments/assets/235e3c55-b663-448e-b17a-53c30d3272fc

Hashed passwords stored in database:
![WhatsApp Image 2025-08-21 at 22 14 17_7a79abfb](https://github.com/user-attachments/assets/d4ce9b9f-176c-47e5-882a-e25171d8ea1d)

Specific roles in database:
![WhatsApp Image 2025-08-21 at 22 14 43_89c9e45a](https://github.com/user-attachments/assets/dee6c04e-b503-4b59-bcdb-cd25b308828e)
