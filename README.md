# trading-tools

## How to add a new button in Home page:
- open **fragment_home.xml**
- add a button component
```
<Button
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_margin="4dp"
    android:text="Button" />
```

## How to interact with this new button:
- open **fragment_home.xml**
- add an id for the button
```
<Button
    android:id="@+id/btn_increase"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_margin="4dp"
    android:text="Button" />
```
- open **HomeFragment.kt**
- in this example below, we set a click event on the button
```
_binding?.btnIncrease?.setOnClickListener {
    // handle the click event here
}
```


