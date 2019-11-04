# Telescope-Simulation

#  This simulation simulates a Sky Night at the University of Arizona Sky Center. The Sky Center has 2 telescopes the public can use, the 32 inch Schulman telescope, and the 24 inch Phillips telescope. When people arrive, they have to make a decision of which telescope to wait in line for. In the simulation, if the line for the Schulman telescope is shorter than the line for the Phillips telescope, the person will choose the Schulman telescope. Otherwise, if the line for the Schulman telescope is long (greater than 15 people), and the person is not super interested in astronomy, they chose the Phillips telescope. Otherwise, if they are very interested in astronomy, regardless of the length of the lines, they choose the Schulman telescope. If they are not very interested in astronomy they choose the Phillips telescope because the line is shorter. 
  
#	Each telescope has a technician who runs it. A technician can either have average abilities with the telescope, good abilities with the telescope, or poor abilities with the telescope. A person spends a specified amount of time at the telescope, but this time is affected by the technician. It takes time to move the telescope and help the person use the eyepiece. If the technician is good, they decrease the time the person spends at the telescope because they know how to move it faster. If the technician is average, they don’t affect the time at the telescope. If the technician is bad, they increase the time the person spends at the telescope because it takes them longer to get it set up. Each technician has a specified shift length. A technician will not change shifts until they are done with their current observer. This means that the technicians for the two telescopes may change shifts at slightly different times. People may arrive at the sky center up until the end of the Sky Night. If people are still waiting in line, they will be served even if it is after hours. 
  
#	Some assumptions that were made with this simulation were that everyone observed individually (not in a group), that weather was conducive to observing during the whole night, that the telescope never broke and needed a longer time to be serviced, that people never cut in line, and that people never left the line after they got in it. 
