# Shopping list project

I created this Project to learn Android, android studio. If you see any bad code practices, feel free to inform me.

I would like to create simple, fast shoping list application with some small features. Main purpose is for daily grocery.

At the moment i use [Out of milk](https://play.google.com/store/apps/details?id=com.capigami.outofmilk) app. It is ok, but i 
think it is too complex and with adverts. So i would make my own app - lean and adjusted for my own shopping experience.

## Recycler view

Main work is with Recycler view in MainActivity. Step by sep i have introduce myself with the 
[documentation](https://developer.android.com/guide/topics/ui/layout/recyclerview).

It is tricky to use swipe for each row and create submenu buttons, but with help of many of tutorials i managed this. In the 
code you can find some links to tutorials.

## Swipe rows

Refactored code. Abandoned swipe with ItemTouchHelper.Callback helper to create my own swipe animation. It will help me to show 
buttons and swipe out the row to procede deletion. Swipe has two tresholds - for menu and swipe to delete. Order move up and 
down still remains with the helper class.

## Undo option

If row is swiped to delete, undo view appears at the botton of the screen. So if you swiped row out accidentaly there is option 
to undo action. This feature i made with touch gestures (View.OnTouchListener) and threads to make animation.

## What is next

I have some ideas to improve user experience to make it more useable. So i am ready to go forward, to learn little bit more.

- Postpone option
- color for each item
- create add button with selector
