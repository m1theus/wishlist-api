db = db.getSiblingDB('wishlist');

db.products.insertMany([
    { id: 1, name: "Ar Condicionado" },
    { id: 2, name: "Liquidificador" }
]);
