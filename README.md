# Software Studio Assignment 6

## Notes
+ You will have to import the libraries on your own. All the libraries are provided on iLMS.
+ Even you followed the design TA shown in the video, you still have to explain the control of the program in the next section.

## Explanation of the Design
Explain your design in this section.  
在Character這個class裡，我用了isFocused來決定圓?的大小、要不要顯示名字；用isAdded來決定圓?該在的位置；用isDragging來判斷要不要讓圓?跟著滑鼠移動。當滑鼠落在圓?的範圍內時，isFocused被設為true，反之為false。isDragging在滑鼠點下圓?的那一刻被設為true，在滑鼠鬆開時設為false。當滑鼠鬆開時有兩種結果，一是圓?在大圓外面，那麼isAdded就被設為false，如果落在大圓裡面，isAdded就被設為true。
circleX, circleY, circleRadius這三個變數是有關於大圓的資訊，orgX,orgY是圓?排在大圓左側時應有的位置，他們都必須在呼叫constructor時傳入。

Example:
### Operation
+ Clicking on the button "Add All": users can add all the characters into network to be analyzed.
+ Clicking on the button "Clear": users can remove all the characters from network.
+ Hovering on the character: the name of the character will be revealed.
+ By dragging the little circle and drop it in the big circle, the character will be added to the network.
+ By pressing key 1~7 on the keyboard, users can switch between episodes.
+ ...etc.

### Visualization
+ The width of each link is visualized based on the value of the link.
+ ...etc.

