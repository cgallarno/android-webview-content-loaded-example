#Determine when an Android's WebView is finished Loading.

###This method relies on two key components.

##First - Html Content
Using Javascript we listened for the document's 'DOMContentLoaded' event, however this seems to fire too soon (at least it does on android). So when we get that event we also check `document.readyState` to see if it's 'complete' and if not we wait 200ms and check again until it is.

Once JS knows that the content is loaded it fires a url request to a specific uri like `myapp://notifications?event=DOMContentLoaded` which is where android takes over.

View the example html and js here [https://jsfiddle.net/qqa1mv3n/1/](https://jsfiddle.net/qqa1mv3n/1/)

##Second - Java
In our activity we listen for route changes in the webview with `shouldOverrideUrlLoading` and check the url to see if it belongs to our custom schema `if (url.startsWith("myapp://notifications"))` Then we can look at the query params to determine the event and run code accordingly.
