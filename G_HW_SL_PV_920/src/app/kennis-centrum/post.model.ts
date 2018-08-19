
export class Post {
    private _id: string;
    private _titel: string;
    private _bericht: string[];
    private _datum: Date;

    constructor(titel: string, bericht: string[], datum: Date) {
        this._titel = titel;
        this._bericht = bericht;
        this._datum = datum;

    }

    static fromJSON(json): Post {
        const post = new Post(json.titel, json.bericht, json.datum);
        post._id = json._id;
        return post;
    }

    get datum(): Date {
        return this._datum;
    }
    get bericht(): string[] {
        return this._bericht;
    }
    get titel(): string {
        return this._titel;
    }
    public toJSON() {
        return {
            titel: this._titel,
            _id: this._id,
            datum: this._datum,
            bericht: this._bericht
        }
    }
}
