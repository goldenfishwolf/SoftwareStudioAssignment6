# Software Studio Assignment 6

## Explanation of the Design
Explain your design in this section.
林軒竹 
在Character這個class裡，我用了isFocused來決定大圓的大小、要不要顯示名字；用isAdded來決定大圓該在的位置；用isDragging來判斷要不要讓大圓跟著滑鼠移動。當滑鼠落在大圓的範圍內時，isFocused被設為true，反之為false。isDragging在滑鼠點下大圓的那一刻被設為true，在滑鼠鬆開時設為false。當滑鼠鬆開時有兩種結果，一是大圓在大圓外面，那麼isAdded就被設為false，如果落在大圓裡面，isAdded就被設為true。 circleX, circleY, circleRadius這三個變數是有關於大圓的資訊，orgX,orgY是大圓排在大圓左側時應有的位置，他們都必須在呼叫constructor時傳入。

在MainApplet裡，我用mouseMoved()提供的資訊來決定character的isFocused;在mousePressed()決定使用者是按下了按鍵或準備開始drag;在mouseDragged()中決定character的位置;在mouseReleased()中決定character的新位置以及處理跟network有關的事。至於episode的轉換則是使用鍵盤的數字鍵1~7來達成。 

Example:
### Operation
+ Clicking on the button "Add All": users can add all the characters into network to be analyzed.
+ Clicking on the button "Clear": users can remove all the characters from network.
+ By dragging the little circle and drop it in the big circle, the character will be added to the network.
+ By pressing key 1~7 on the keyboard, users can switch between episodes.
+ ...etc.

### Visualization
+ The width of each link is visualized based on the value of the link.
+ ...etc.

吳俊德
在network裡，我用addNetwork來把被滑鼠拖曳進圈圈的character加進Network裡的Arraylist裡，而在display函數裡，會將所有在圈圈裡的character根據關係來連線
另外，clearall就是負責把所有在圈圈上的character清空，deductNetwork是把某個特定的character清掉