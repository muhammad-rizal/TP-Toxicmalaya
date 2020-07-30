var num=0;
function swapTiles(cell1,cell2) {
  var temp = document.getElementById(cell1).className;
  document.getElementById(cell1).className = document.getElementById(cell2).className;
  document.getElementById(cell2).className = temp;
}

function shuffle() {
num=0;
$('#num').html(num);
for (var row=1;row<=3;row++) { 
   for (var column=1;column<=3;column++) { 
  
    var row2=Math.floor(Math.random()*3 + 1); 
    var column2=Math.floor(Math.random()*3 + 1);
     
    swapTiles("cell"+row+column,"cell"+row2+column2);
  } 
} 
}

function clickTile(row,column) {
  if (num==0) {
    $('#num').html(num++);
  }
  var cell = document.getElementById("cell"+row+column);
  var tile = cell.className;
  if (tile!="tile9") { 
       if (column<3) {
         if ( document.getElementById("cell"+row+(column+1)).className=="tile9") {
           swapTiles("cell"+row+column,"cell"+row+(column+1));
           $('#num').html(num++);
           return;
         }
       }
       if (column>1) {
         if ( document.getElementById("cell"+row+(column-1)).className=="tile9") {
           swapTiles("cell"+row+column,"cell"+row+(column-1));
           $('#num').html(num++);
           return;
         }
       }
       if (row>1) {
         if ( document.getElementById("cell"+(row-1)+column).className=="tile9") {
           swapTiles("cell"+row+column,"cell"+(row-1)+column);
           $('#num').html(num++);
           return;
         }
       }
       if (row<3) {
         if ( document.getElementById("cell"+(row+1)+column).className=="tile9") {
           swapTiles("cell"+row+column,"cell"+(row+1)+column);
           $('#num').html(num++);
           return;
         }
       } 
  }  
}

function goal(){
 
}