class Node {
    constructor(parent) {
        this.p = parent;
        this.loc = createVector(random(width), random(height))
        this.vel = createVector(random(-1, 1), random(-1, 1));
        this.color = color(random(255), random(255), random(255));
        this.range = random(0, 1);
        this.connectLimit = Math.floor(random(0, 5));
        this.nearest = [];
    }

    checkNearest(nodes){
        let distanceArray = nodes.map(node => distance(this.loc.x,node.loc.x,this.loc.y,node.loc.y));
        for (let i = 0; i < this.p.connectLimit; i++){
            this.nearest.push(nodes[distanceArray.indexOf(Math.min(distanceArray))]);
        }
    }

    update() {
        this.checkNearest(this.p.nodes);
        this.vel.normalize();
        let actRange = map(range, 0, 1, this.p.minRange, this.p.maxRange);
        let drift = createVector(map(this.vel.x, -1, 1, -this.p.driftLimit, this.p.driftLimit), map(vel.y, -1, 1, -this.p.driftLimit, this.p.driftLimit));
        this.loc.x = constrain(loc.x + drift.x, 0, width);
        this.loc.y = constrain(loc.y + drift.y, 0, height);
        if (this.p.loopingEdges) {
            if (this.loc.x == 0 || this.loc.x == width) {
                this.vel.x = -this.vel.x;
            }

            if (this.loc.y == 0 || this.loc.y == height) {
                this.vel.y = -this.vel.y;
            }
        } else {
            if (this.loc.x == 0) {
                this.loc.x = width - 1;
            }
            if (this.loc.x == width) {
                this.loc.x = 1;
            }

            if (this.loc.y == 0) {
                this.loc.y = height - 1;
            }
            if (this.loc.y == height) {
                this.loc.y = 1;
            }
        }
    }

    display() { }
}

class NodeCollector {
    constructor(sketch) {
        this.connectLimit = 4;
        this.s = sketch;
        this.minRange = 25;
        this.maxRange = 300;
        this.uniformRange = 150;
        this.alpha = 20;
        this.nodeCount = 40;
        this.driftLimit = 1;
        this.sparkSize = 3;
        this.displacement = 1;
        this.connectionsOn = true;
        this.singleColor = false;
        this.rangeBased = true;
        this.singleRange = true;
        this.sparklesOn = true;
        this.coresOn = true;
        this.rangesOn = false;
        this.loopingEdges = false;
        this.uniformColor = color(66, 66, 207);
        this.bg = color(0, 0, 0);
        this.sparkStyle = 1;
        this.sparkleColor = color(255, 255, 255);
        this.nodes = [];
    }
}

class Drifter { }

class DrifterCol { }

class Path { }

class PathManager { }

function setup(){}

function draw(){}