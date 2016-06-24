# SimpleFloatButton
Hi, bros.

I creat a simple float button in here.

If you will any question, you can email to me.

# How to use

You can see [FloatButtonLayout](https://github.com/Kuan-Wei-Kuo/SimpleFloatButton/blob/master/app/src/main/java/com/kuo/simplefloatbutton/FloatButtonLayout.java), it's a buttons manager.

Step 1. Add button in view.

```
ImageButton addButton = new ImageButton(this);
addButton.setLayoutParams(layoutParams);
addButton.setBackgroundResource(R.drawable.add_button);

ImageButton deleteButton = new ImageButton(this);
deleteButton.setLayoutParams(layoutParams);
deleteButton.setBackgroundResource(R.drawable.delete_button);

mainLayout.addView(deleteButton);
mainLayout.addView(addButton);

floatButtonLayout = new FloatButtonLayout();
floatButtonLayout.addButton(deleteButton);
floatButtonLayout.addButton(addButton); 
```

Step 2. Set item animation
```
private FloatButtonLayout.OnItemAnimator onStartItemAnimator = new FloatButtonLayout.OnItemAnimator() {
        @Override
        public void setItemAnimator(View button, int index) {
            switch (index){
                case 0:
                    //Delete Button Animator
                    ViewPropertyAnimatorCompat deleteAnimator = ViewCompat.animate(button);
                    deleteAnimator
                            .translationY(-(button.getHeight() + 20))
                            .setInterpolator(new OvershootInterpolator())
                            .start();
                    break;
                case 1:
                    //Add Button Animator
                    ViewPropertyAnimatorCompat addAnimator = ViewCompat.animate(button);
                    addAnimator.rotation(405).start();
                    break;
            }
        }
    };
```
Step 3. Use FloatButtonLayout.setOnItemAnimator and FloatButtonLayout.startAnimator 
```
floatButtonLayout.setOnItemAnimator(onBackItemAnimator);
floatButtonLayout.startAnimator();
```
***

#Voice
[![Voice](https://lh3.googleusercontent.com/M1QK2wpOTa9gT3GA_7PqFK2IT0swoPlVibSxfDmujJc=w854-h509-no)](https://youtu.be/L2CXUIlsnqU)
