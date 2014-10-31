import com.mongodb.BasicDBList
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient

def mongoClient = new MongoClient()
def collection = mongoClient.getDB("bubbleTea").getCollection('BubbleTeaShop')
collection.drop()

def xmlSlurper = new XmlSlurper().parse(new File('bubble_tea_places'))

xmlSlurper.node.each { child ->
    Map bubbleTeaShop = ['openStreeetMapId': child.@id.text(),
                         'location'        : ['coordinates': [Double.valueOf(child.@lon.text()),
                                                              Double.valueOf(child.@lat.text())],
                                              'type'       : 'Point']]
    child.tag.each { theNode ->
        def fieldName = theNode.@k.text()
        if (!fieldName.contains(".") && fieldName != 'location') {
            bubbleTeaShop.put(fieldName, theNode.@v.text())
        }
    }
    if (bubbleTeaShop.name != null) {
        collection.insert(new BasicDBObject(bubbleTeaShop))
    }
}

collection.createIndex(new BasicDBObject('location', '2dsphere'))



