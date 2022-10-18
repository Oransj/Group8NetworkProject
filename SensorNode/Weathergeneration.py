import random

def split_into_values(weightlist : list):
    parameterlist = []
    for line in weightlist:
        thisparameter = []
        splitline = line.split(':')
        thisparameter.append(splitline[0])
        parameter = splitline[1].split(' ')
        parameter = list(filter(None, parameter))
        thisparameter.append(parameter)
        parameterlist.append(thisparameter)
    return parameterlist

def generate_weather(parameters : list):
    weather = []
    for type in parameters:
        if(random.randrange(0,100) < float(type[1][0]) * 100):
            weather.append(random.uniform(float(type[1][1]), float(type[1][2])))
        else:
            weather.append(0)
    print(weather)
        
    


reader = open('Weighting.txt', 'r')
weight = reader.read()
weightlist = weight.split('\n')
del weightlist[0]
settings = split_into_values(weightlist)
print(settings)
generate_weather(settings)