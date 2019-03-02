<div align="center">
   <img src="HelpOnTheWay/app/libs/logo.png" width="150px">
</div>

# Help On The Way
17781 Android Project

# Team6 
Andrew Heitmann<br>
Mingyang Li<br>
Yuqi Sun<br>
Junxue Zhu<br>



# Git experience to improve teamwork efficiency
* The master branch is protected. It can only be updated by pull request. Direct push does not work.
* Create a new branch by **git checkout -b <branch_name>**, push the branch, and create a pull request in github console.
* Please ensure **.gitignore** is included and manually check to avoid unnecessary files.
* Run **grep -r password .** to avoid pushing sensitive information.
* Run checkstyle with **java -jar checkstyle.jar -c /google_java_style_revised.xml .** before commit.
* Between **git add** and **git commit**, run **git diff --cached** to double check the content.
* If you accidentally work on the master branch, follow these steps:
   1. run **git checkout -b <branch_name>** to create a new branch
   2. push the new branch and verify in github console
   3. checkout master branch **git branch master**
   4. run **git reset HEAD~** to roll back local master branch.
