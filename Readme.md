# 100 days of code

an app about 100 days of code.

```
- CurrentCountdownActivity
    > 00/100
    > January First/1/2019
    >
        vvvvvvvvvv---- (In a box)
        last coded : xxx hours ago
        ----------------------------------------------
        "have you coded for atLeast 1 hour today?"
        Yes No
        ----------------------------------------------
        on yes click, it will change the box to a jumping tick icon with message "yay!, share your progress
        with your friends!" and point to share button.will also show a snackbar saying "congratulations!" and undo button

        on clicking no. it will change the box to keep coding! with a snackbar " we will keep reminding you every for
        hours for the same" and undo button
    > edit button : to change all the current steaks info in a bottom sheet
    > motivation quotes recycler view

- settings in menubar
    > open settings activity( control reminder delay setting, other settings)
    > reset day count : will show a toast" your countdown is resetted" and will take back to "StartCountdownActivity"
    > about page


- settings activity
    >  control reminder delay setting,...other settings

- shareProgressActivity
    > shows a cool card related to progress with an edit text to add message and a share button. on
      pressing share button, shares card and text with social media


- startCoundownActivity (DashBoardActivity)
    > current streak card
    > history menu: a small card like box with last streaks and more button. on clicking more,  can open
      complete history activity
    > start a new streak menu: a bunch of check boxes and edit texts to get details for new streak and
      a start button.(basically, this will act like a  general place for describing a streak, as we
      will convert it into a resolution app)

- MainActivity
    > no ui/no layout
    > oly decides which activity to load StartCountdownActivity or CurrentStreakActivity

```