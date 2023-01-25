class Node {
    constructor(parent) {
        this.p = parent;
        this.loc = this.p.s.createVector(this.p.s.random(this.p.s.width), this.p.s.random(this.p.s.height))
        this.vel = this.p.s.createVector(this.p.s.random(-1, 1), this.p.s.random(-1, 1));
        this.color = this.p.s.color(this.p.s.random(255), this.p.s.random(255), this.p.s.random(255));
        this.range = this.p.s.random(0, 1);
        this.connectLimit = Math.floor(this.p.s.random(0, 5));
        this.nearest = [];
    }

    checkNearest(nodes){
        let distanceArray = nodes.map(node => this.p.s.distance(this.loc.x,node.loc.x,this.loc.y,node.loc.y));
        for (let i = 0; i < this.p.connectLimit; i++){
            this.nearest.push(nodes[distanceArray.indexOf(Math.min(distanceArray))]);
        }
    }

    update() {
        this.checkNearest(this.p.nodes);
        this.vel.normalize();
        let actRange = this.p.s.map(range, 0, 1, this.p.minRange, this.p.maxRange);
        let drift = this.p.s.createVector(this.p.s.map(this.vel.x, -1, 1, -this.p.driftLimit, this.p.driftLimit), map(vel.y, -1, 1, -this.p.driftLimit, this.p.driftLimit));
        this.loc.x = constrain(loc.x + drift.x, 0, this.p.s.width);
        this.loc.y = constrain(loc.y + drift.y, 0, this.p.s.height);
        if (this.p.loopingEdges) {
            if (this.loc.x == 0 || this.loc.x == this.p.s.width) {
                this.vel.x = -this.vel.x;
            }

            if (this.loc.y == 0 || this.loc.y == this.p.s.height) {
                this.vel.y = -this.vel.y;
            }
        } else {
            if (this.loc.x == 0) {
                this.loc.x = this.p.s.width - 1;
            }
            if (this.loc.x == this.p.s.width) {
                this.loc.x = 1;
            }

            if (this.loc.y == 0) {
                this.loc.y = this.p.s.height - 1;
            }
            if (this.loc.y == this.p.s.height) {
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

const nodes = new p5((sketch) => {
    sketch.setup = () => {
        let c = sketch.createCanvas(400, 400);
        c.parent("graphCanvas");
        sketch.background(0);
    }
});

const warp = new p5((sketch) => { });

const flow = new p5((sketch) => { });

const paths = new p5((sketch) => { });

const cells = new p5((sketch) => { });