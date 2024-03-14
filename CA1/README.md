# CA1 - Version Control with Git

## Part1: Initial Setup and Basic Configuration (everything in the main branch)
- Clone the project from GitHub into the ***DevOps*** folder:
```bash
git clone git@github.com:spring-guides/tut-react-and-spring-data-rest.git
```

- Create a new directory called CA1
```bash
mkdir CA1 
```
- Initialize a new Git repository and configure user information:
```bash
git init
git config user.name “Pedro Gil”
git config user.email “1231851@isep.ipp.pt”
```
(git init command: is used to initialize a new Git repository in a directory)


-  Create a new repository on GitHub named “devops-23-24-JPE-1231851” and link your local repository to GitHub:
```bash
git remote add origin git@github.com:Departamento-de-Engenharia-Informatica/devops-23-24-JPE-1130122.git
```
-  Set the working branch as main and move the "tut-react-and-spring-data-rest-main" directory:
```bash
git branch -M main
mv ./DevOps/tut-react-and-spring-data-rest-main ./CA1
```
- Since there were still remnant files from the previous repository (including .git) , I removed them manually.  
(git branch -M main: is used to rename the current branch to main. The -M option is a shorthand for --move, which is used to rename the branch.)
(Found conflicts while trying to use the command line)


#### Committing and Pushing Changes

- Add all files to the staging area and commit the changes:
```bash
git add .
git commit -m "[Initial] #1 - Initial commit with the moved tut-react-and-spring-data-rest application in 
the CA1 folder"
git tag v1.1.0
git push -u origin main --tags
```
(manually closed Issue #1 on GitHub since I forgot to add the "Close" keyword in the commit message)

- Add a new field to record the years of the employee in the company (e.g., jobYears)  and commit these changes: 
```bash
git add .
git commit -m "[Feat] - Added JobYears field, updated respective tests in EmployeeTest, and updated DatabaseLoader class  
with an additional field. Close #2"
git tag v1.2.0
git push -u origin main --tags
```
- Add a new field to record the years of the employee in the company (e.g., jobYears)  and commit these changes:
```bash
git add . 
git commit -m "[Feat] close #3 - Added JobTitle field, Employee class with tests, and updated DatabaseLoader class 
with an additional field."
git tag v1.2.1
```
(Made a mistake of calling the tag "v1.2.1" instead of "v1.2.0")
- At the end of the assignment mark your repository with the tag ca1-part1.
```bash
git tag ca1-part1
git push -u origin main --tags
```

## Part 2: Using Branches 
### Email-field branch
- Create a branch named ”email-field” to add a new email field to the application.
```bash
git checkout -b email-field
```
(git checkout -b: is used to create a new branch and switch to it)
-  Confirm the branch creation and status:
```bash
git branch -a
git status
```
- Add the email field, commit, and push changes to the new branch:
```bash
git add .
git commit -m "[Feat] #4 - Added email field, updated EmployeeTest class with tests, and updated DatabaseLoader class with one more field."
git push origin email-field
```
- Merge changes from the feature branch back into the main branch:
```bash
git checkout main
git pull
git merge email-field
git tag v1.3.0
git push -u origin main --tags
```
### Fix-invalid-email branch
- Create a branch named ”fix-invalid-email” to add a new features.
```bash
git checkout -b email-field
```
-  Confirm the branch creation and status:
```bash
git branch -a
git status
```
- After fixing and testing, add, commit, and push the bug fix:
```bash
git add .
git commit -m "[Feat] #5 - Updated Email-field validations, and added Tests."
git push origin fix-invalid-email
```
- Merge changes from the feature branch back into the main branch:
```bash
git checkout main
git pull
git merge Fix-invalid-email
```
- new tag should be created (with a change in the minor number, e.g., v1.3.0 -> v1.3.1)
```bash
git tag -d v1.3.0
git pusg origin :refs/tags/v1.3.0
git tag v1.3.0
git push -u origin main --tags
```
- At the end of the assignment mark your repository with the tag ca1-part2.
```bash
git tag ca1-part2 
git push origin ca1-part2
```
## Part3: Alternative technological solution for version control (i.e., not based on Git).
### Mercurial as an Alternative

Mercurial (Hg) is another distributed version control system similar to Git. While both Git and Mercurial share many 
concepts and features, they have their own unique approaches to version control and project management. 
Understanding Mercurial as an alternative can provide insights into different workflows and collaboration styles.

### Comparison to Git

1. Decentralized Nature: Like Git, Mercurial is a distributed version control system, meaning each developer has a complete repository copy, allowing for offline work and easier collaboration among distributed teams.

2. Branching and Merging: Mercurial's branching and merging capabilities are comparable to Git's, allowing developers to create lightweight branches for features and fixes, and merging them back into the mainline when ready. The process is intuitive and similar to Git's workflow.

3. Performance and Efficiency: Mercurial performs well for most operations, offering similar speed and efficiency to Git. While there may be differences in specific use cases, both systems generally handle projects of varying sizes effectively.

4. Ease of Use: Mercurial is often praised for its user-friendly interface and straightforward commands, making it accessible to developers with varying levels of experience. Its documentation is also comprehensive and easy to follow.

## Implementing the Assignment Goals with Mercurial

- Clone the Repository:
```bash
hg clone git@github.com:spring-guides/tut-react-and-spring-data-rest.git
```
- Create Mercurial Repository:
```bash
hg init 
```
- Commit Changes, tag, and push to the main branch:
```bash
hg commit -m "[Initial] #1 - Initial commit with the moved tut-react-and-spring-data-rest application in 
the CA1 folder"
hg tag v1.1.0
hg push
```
- Create a new branch named (ex:"email-field") and add the email field:
```bash
hg branch email-field
```
- Merging the Feature Branch
```bash
hg update main
hg merge email-field
hg commit -m "[Feat] #4 - Added email field, updated EmployeeTest class with tests, and updated DatabaseLoader class 
with one more field."
hg tag v1.3.0
hg push
```
## Conclusion
While there are differences in commands and workflows compared to Git, the fundamental concepts remain similar, 
allowing for efficient collaboration and version control. Mercurial's user-friendly interface and distributed nature 
make it a viable option for teams seeking alternatives to Git.
