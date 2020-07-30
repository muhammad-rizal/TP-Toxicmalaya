var num=0;
function swapTiles(cell1,cell2) {
  var temp = document.getElementById(cell1).className;
  document.getElementById(cell1).className = document.getElementById(cell2).className;
  document.getElementById(cell2).className = temp;
}

function shuffle() {
num=0;
$('#num').html(num);
for (var row=1;row<=4;row++) { 
   for (var column=1;column<=4;column++) { 
  
    var row2=Math.floor(Math.random()*4 + 1); 
    var column2=Math.floor(Math.random()*4 + 1); 
     
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
  if (tile!="tile16") { 
       if (column<4) {
         if ( document.getElementById("cell"+row+(column+1)).className=="tile16") {
           swapTiles("cell"+row+column,"cell"+row+(column+1));
           $('#num').html(num++);
           return;
         }
       }
       if (column>1) {
         if ( document.getElementById("cell"+row+(column-1)).className=="tile16") {
           swapTiles("cell"+row+column,"cell"+row+(column-1));
           $('#num').html(num++);
           return;
         }
       }
       if (row>1) {
         if ( document.getElementById("cell"+(row-1)+column).className=="tile16") {
           swapTiles("cell"+row+column,"cell"+(row-1)+column);
           $('#num').html(num++);
           return;
         }
       }
       if (row<4) {
         if ( document.getElementById("cell"+(row+1)+column).className=="tile16") {
           swapTiles("cell"+row+column,"cell"+(row+1)+column);
           $('#num').html(num++);
           return;
         }
       } 
  }
  
}