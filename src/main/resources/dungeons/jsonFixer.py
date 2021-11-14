
import json

def myFix():
    # Opening JSON file
    f = open('ally-support.json',)
    
    # returns JSON object as
    # a dictionary
    data = json.load(f)
    
    # Iterating through the json
    # list
    for i in data['entities']:
        #i["x"] = i["x"] + 1
        i["y"] = i["y"] + 1
    
    
    # Closing file
    f.close()

    json_object = json.dumps(data, indent = 4)
  
    # Writing to sample.json
    with open("ally-support.json", "w") as outfile:
        outfile.write(json_object)

if __name__ == '__main__':
    myFix()
    print("We did it")