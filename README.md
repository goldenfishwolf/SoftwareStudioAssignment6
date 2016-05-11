# Software Studio Assignment 6

## Explanation of the Design
Explain your design in this section.  
林軒竹

在Character這個class裡，我用了isFocused來決定大圓的大小、要不要顯示名字。當滑鼠落在大圓的範圍內時，isFocused被設為true，反之為false。circleX, circleY, circleRadius這三個變數是有關於大圓的資訊，orgX,orgY是大圓排在大圓左側時應有的位置，他們都必須在呼叫constructor時傳入。

在MainApplet裡，我用mouseMoved()提供的資訊來決定character的isFocused;在mousePressed()決定使用者是按下了按鍵或準備開始drag;在mouseDragged()中決定character的位置;在mouseReleased()中決定character的新位置以及處理跟network有關的事。至於episode的轉換則是使用鍵盤的數字鍵1~7來達成。indexDragged這個變數代表的是現在被drag的character的index，default值為-1，它也可以被用來判斷現在滑鼠是否在drag某個character。

Network我做了一些修改，因為有時候點Clear All鍵時會出現ConcurrentModificationException，所以我用boolean isDispalying和callClearallLater來解決display()和clearall()這兩個method同時讀、寫ArrayList的問題。

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

